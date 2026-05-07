package com.student;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainApp {

    static SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Student.class)
            .buildSessionFactory();

    public static void main(String[] args) {
    	
        System.out.println("Project Started");
        
        insertStudent();
        getAllStudents();
        getStudentById(201);
        updateStudent(201, "jackson", "Hibernate");
        deleteStudent(202);

        factory.close();
    }

    public static void insertStudent() {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Student s1 = new Student(201, "jack", "jack@gmail.com", "Java");
        Student s2 = new Student(202, "Roy", "roy@gmail.com", "Python");
        Student s3 = new Student(202, "Anie", "anie@gmail.com", "Spring");

        session.save(s1);
        session.save(s2);
        session.save(s3);

        tx.commit();
        session.close();

        System.out.println("Students Inserted Successfully");
    }

    public static void getAllStudents() {
        Session session = factory.openSession();

        List<Student> students = session
                .createQuery("from Student", Student.class)
                .list();

        System.out.println("\nAll Students:");
        for (Student s : students) {
            System.out.println(s);
        }

        session.close();
    }

    public static void getStudentById(int id) {
        Session session = factory.openSession();

        Student s = session.get(Student.class, id);

        System.out.println("\nStudent By ID:");
        System.out.println(s);

        session.close();
    }

    public static void updateStudent(int id, String newName, String newCourse) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Student s = session.get(Student.class, id);

        if (s != null) {
            s.setName(newName);
            s.setCourse(newCourse);
            session.update(s);
            System.out.println("\nStudent Updated Successfully");
        }

        tx.commit();
        session.close();
    }

    public static void deleteStudent(int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Student s = session.get(Student.class, id);

        if (s != null) {
            session.delete(s);
            System.out.println("\nStudent Deleted Successfully");
        }

        tx.commit();
        session.close();
    }
}