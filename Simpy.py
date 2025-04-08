import simpy
import random
import matplotlib.pyplot as plt

class Technology:
    def __init__(self, env, num_xray, num_ct):
        self.xray = simpy.PriorityResource(env, capacity=num_xray)
        self.ct = simpy.PriorityResource(env, capacity=num_ct)  # Máquina de tomografía

class Personnel:
    def __init__(self, env, num_doctors, num_nurses):
        self.num_doctors = num_doctors
        self.num_nurses = num_nurses
        self.available_doctors = num_doctors * 2  # Cada doctor atiende hasta 2 pacientes
        self.available_nurses = num_nurses * 4  # Cada enfermera atiende hasta 4 pacientes
        self.doctors = simpy.Resource(env, capacity=num_doctors * 2)
        self.nurses = simpy.Resource(env, capacity=num_nurses * 4)

class Beds:
    def __init__(self, env, num_beds):
        self.beds = simpy.Resource(env, capacity=num_beds)

class OperatingRooms:
    def __init__(self, env, num_or):
        self.or_rooms = simpy.Resource(env, capacity=num_or)  # Quirófanos disponibles

class EmergencyRoom:
    def __init__(self, env, num_doctors, num_nurses, num_xray, num_ct, num_beds, num_or):
        self.env = env
        self.technology = Technology(env, num_xray, num_ct)
        self.personnel = Personnel(env, num_doctors, num_nurses)
        self.beds = Beds(env, num_beds)
        self.operating_rooms = OperatingRooms(env, num_or)
        self.wait_times = []

    def triage(self, patient_id):
        with self.personnel.nurses.request() as req:
            yield req
            severity = random.randint(1, 5)
            estimated_time = random.randint(10, 30)
            yield self.env.timeout(estimated_time)
            return severity

    def treatment(self, patient_id, severity):
        with self.personnel.doctors.request() as doc_req:
            yield doc_req
            with self.beds.beds.request() as bed_req:
                yield bed_req  # Espera a que haya una cama disponible
                yield self.env.timeout(random.randint(15, 30))
                self.wait_times.append(self.env.now)
                severity = 0  # El paciente es dado de alta y su severidad se reinicia

    def emergency_patient(self, patient_id):
        """Proceso para pacientes imprevistos con nivel de urgencia 1"""
        with self.personnel.doctors.request() as req:
            yield req  # Paciente atendido inmediatamente por un doctor aleatorio
            with self.operating_rooms.or_rooms.request() as or_req:
                yield or_req  # Asegura disponibilidad de quirófano
                yield self.env.timeout(random.randint(20, 40))  # Tiempo de atención en quirófano
                self.wait_times.append(self.env.now)

    def patient_process(self, patient_id):
        severity = yield self.env.process(self.triage(patient_id))
        if severity == 1:
            yield self.env.process(self.emergency_patient(patient_id))
        else:
            yield self.env.process(self.treatment(patient_id, severity))

    def run_simulation(self, num_patients):
        for i in range(num_patients):
            self.env.process(self.patient_process(i))
            yield self.env.timeout(random.expovariate(1.0 / 5))

def run_emergency_room_simulation(num_doctors, num_nurses, num_xray, num_ct, num_beds, num_or, num_patients):
    env = simpy.Environment()
    er = EmergencyRoom(env, num_doctors, num_nurses, num_xray, num_ct, num_beds, num_or)
    env.process(er.run_simulation(num_patients))
    env.run(until=500)
    return er.wait_times

# Simulación con 2 doctores, 3 enfermeras, 1 máquina de rayos X, 1 tomógrafo, 5 camas y 2 quirófanos
wait_times = run_emergency_room_simulation(2, 3, 1, 1, 5, 2, 50)
plt.hist(wait_times, bins=20, edgecolor='black')
plt.xlabel('Tiempo de espera')
plt.ylabel('Cantidad de pacientes')
plt.title('Distribución de tiempos de espera en la sala de emergencias')
plt.show()
