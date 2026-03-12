import java.util.*;
import java.time.*;
import java.time.format.*;

class Doctor {

    String name;
    String specialization;
    int startHour;
    int endHour;

    // CO2 – ADT using Objects stored in Array
    Doctor(String name, String specialization, int startHour, int endHour){
        this.name = name;
        this.specialization = specialization;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}

class Appointment {

    String patientName;
    String contact;
    Doctor doctor;
    String date;
    String time;

    Appointment(String patientName, String contact, Doctor doctor, String date, String time){
        this.patientName = patientName;
        this.contact = contact;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public String toString(){
        return "Patient: " + patientName +
                " | Contact: " + contact +
                " | Doctor: " + doctor.name +
                " (" + doctor.specialization + ")" +
                " | Date: " + date +
                " | Time: " + time;
    }
}

public class HospitalAppointmentWebsite {

    // CO2 – Array ADT storing doctor objects
    static Doctor[] doctors = {
            new Doctor("Dr Sharma","Cardiologist",10,12),
            new Doctor("Dr Mehta","Dermatologist",12,14),
            new Doctor("Dr Reddy","Orthopedic",14,16),
            new Doctor("Dr Rao","Neurologist",16,18)
    };

    // CO3 – Queue using LinkedList (FIFO appointment handling)
    static Queue<Appointment> appointments = new LinkedList<>();


    // CO5 – Traversing Array to display doctors
    static void viewDoctors(){

        System.out.println("\nAvailable Doctors:");

        for(int i=0;i<doctors.length;i++){

            System.out.println((i+1)+". "
                    + doctors[i].name
                    + " - "
                    + doctors[i].specialization
                    + " | Available: "
                    + doctors[i].startHour + ":00 - "
                    + doctors[i].endHour + ":00");

        }
    }


    // CO1 – Searching algorithm (Linear Search logic)
    static Doctor findDoctor(int choice){

        if(choice>=1 && choice<=doctors.length){
            return doctors[choice-1];
        }

        return null;
    }


    // CO1 – Date validation with algorithmic logic
    static boolean isValidDate(String date){

        try{

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            LocalDate inputDate =
                    LocalDate.parse(date, formatter);

            LocalDate today = LocalDate.now();

            if(inputDate.isBefore(today)){
                System.out.println("❌ Invalid Date! Past date not allowed.");
                return false;
            }

            return true;

        }catch(Exception e){

            System.out.println("❌ Invalid Date Format! Use dd-MM-yyyy");
            return false;

        }
    }


    // CO1 – Time format validation
    static boolean isValidTime(String time){

        try{

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("HH:mm");

            LocalTime.parse(time, formatter);

            return true;

        }catch(Exception e){

            System.out.println("❌ Invalid Time Format! Use HH:mm");
            return false;

        }
    }


    // Check doctor availability
    static boolean isDoctorAvailable(Doctor doctor, String time){

        int hour = Integer.parseInt(time.split(":")[0]);

        if(hour < doctor.startHour || hour >= doctor.endHour){

            System.out.println("❌ Doctor not available at this time.");
            System.out.println("Doctor Available Time: "
                    + doctor.startHour + ":00 - "
                    + doctor.endHour + ":00");

            return false;
        }

        return true;
    }


    static void bookAppointment(Scanner sc){

        viewDoctors();

        System.out.print("Choose Doctor Number: ");
        int docChoice = sc.nextInt();
        sc.nextLine();

        Doctor doctor = findDoctor(docChoice);

        if(doctor==null){
            System.out.println("❌ Invalid doctor selection");
            return;
        }

        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email / Phone: ");
        String contact = sc.nextLine();

        System.out.print("Enter Appointment Date (dd-MM-yyyy): ");
        String date = sc.nextLine();

        if(!isValidDate(date)){
            return;
        }

        System.out.print("Enter Appointment Time (HH:mm): ");
        String time = sc.nextLine();

        if(!isValidTime(time)){
            return;
        }

        if(!isDoctorAvailable(doctor,time)){
            return;
        }

        Appointment ap =
                new Appointment(name,contact,doctor,date,time);

        // CO3 – Queue insertion operation
        appointments.add(ap);

        System.out.println("\n✅ Appointment Booked Successfully!");
        System.out.println(ap);
    }


    // CO3 – Queue traversal
    static void viewAppointments(){

        if(appointments.isEmpty()){
            System.out.println("No appointments yet.");
            return;
        }

        System.out.println("\nAppointment List:");

        for(Appointment a : appointments){
            System.out.println(a);
        }
    }


    // CO6 – Complete Java Application
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        while(true){

            System.out.println("\n===== Hospital Appointment Website =====");

            System.out.println("1. View Doctors");
            System.out.println("2. Book Appointment");
            System.out.println("3. View All Appointments");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){

                case 1:
                    viewDoctors();
                    break;

                case 2:
                    bookAppointment(sc);
                    break;

                case 3:
                    viewAppointments();
                    break;

                case 4:
                    System.out.println("Thank you");
                    System.exit(0);

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
