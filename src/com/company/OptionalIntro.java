package com.company;

import org.w3c.dom.ls.LSOutput;

import java.util.Optional;

public class OptionalIntro {
    public static void main(String[] args) {

        // tworzymy zmienną employee która zaweira obiekt pracownika, lub jest pusta gdy go nie znajdzie
        Optional<Employee> employee = EmployeeRepository.find("Paweł");

        if(employee.isPresent()) { // sprawdzamy czy jest
            Employee emp = employee.get(); // jak jest to zwróć mi ten obiekt
            System.out.println(emp.getName());
        }
        // daje nam to bezpieczenstwo, nawet jak kogos nie ma to nie wywali exception
        // możemy też skrócić ifa do:

        employee.ifPresent(emp -> System.out.println(emp.getName()));

        // metoda ifPresent przyjmuje jako argument consumer
        // consumer to interfejs funkcyjny z metodą accept która przyjmuje dany typ <>
        // i nie zwraca nic

        // metoda map modyfikuje jeden typ w drugi
        // zapisując do zmiennej:
        Optional<String> s = employee.map(Employee::getName);

        employee
                .map(Employee::getName)             // modyfikacja typu employee na string
                .ifPresent(System.out::println);    // i wypisanie stringa w locie


        // standardowy odpowiednik:
        // if(employee != null) {
        // String Name = employee.getName();
        // System.out.println(name);
        // }

        // metoda filter zwraca tylko takie obiekty które spełniają warunki w argumencie
        // np tylko pracownicy po 20
        employee
                .filter(emp -> emp.getAge()>=20)     // to samo ale tylko dla obiektów +20 age
                .map(Employee::getName)
                .map(String::toUpperCase)           // niech będzie wielkimi literami
                .ifPresent(System.out::println);

        Optional<Employee> employee2 = EmployeeRepository.find("Edek");

        employee2
                .filter(emp -> emp.getAge()>=20)     // tu nie wejdzie w widełki
                .map(Employee::getName)
                .ifPresent(System.out::println);

        // a co jak obiektu nie ma:
        Optional<Employee> employee3 = EmployeeRepository.find("Jacek");

        String nameEmployee = employee3
                .filter(emp -> emp.getAge()>=20)       // tu nie znajdzie jacka
                .map(Employee::getName)
                .orElse("BRAK PRACOWNIKA");        // zamiast zwykłego else

        System.out.println(nameEmployee);               // tu nie wypisze bo nie ma

        //nameEmployee = employee3
                //.filter(emp -> emp.getAge()>=20)
                //.map(Employee::getName)
                //.orElseThrow(() -> new RuntimeException("BRAK PRACOWNIKA"));        // albo inaczej
                // ta linia rzuci wyjątkiem

        System.out.println(nameEmployee);

        // z optionala można zrobić  stream, jedno lub wielo elementowy jeśli w optionalu jest lista elementów
        // .straem()

        Optional<Employee> employee4 = EmployeeRepository.find("Adam");

        employee4
                .or(()->Optional.of(new Employee("Adam", 42))) // jeśli takiego pracownika nie ma
                .filter(emp -> emp.getAge()>=20)                           // to zrób i prześlij go dalej
                .map(Employee::getName)
                .map(String::toUpperCase)
                .ifPresent(System.out::println);

        Optional<Employee> employee5 = EmployeeRepository.find("Piotr");

        employee5
                .or(()->Optional.of(new Employee("Michał", 18)))
                .filter(emp -> emp.getAge()>=20)                        // jeśli za młody można zmienić metodę
                .map(Employee::getName)
                .map(String::toUpperCase)
                .ifPresentOrElse(                           // jeśli wartość jest to coś zrób, a jak nie
                        System.out::println,                // wywoła metodę bezargumentową, np sout("")
                        ()->System.out.println("BRAK PRACOWNIKA O TAKICH PARAMETRACH")
                );
        // pobieramy typ optional, szukamy pracownika5 piotra
        // piotr nie istnieje w bazie danych, odpalił się or
        // dostarczyliśmy do naszego łańcucha wywołań nowego pracownika michała
        // michał nie przeszedł przez filtr 20+
        // dla kolejne optionale map są puste
        // ifPresent dostaje pustego optionala więc wie że ma wywołać implementacje interfejsu runnable

    }
}
