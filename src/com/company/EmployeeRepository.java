package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepository {

    private static Map<String,Employee> employees = new HashMap<>(2);

    static {
        employees.put("Paweł", new Employee("Paweł", 27));
        employees.put("Edek", new Employee("Edek", 19));
    }

    private EmployeeRepository(){

    }

    //public static Employee find(String name){
        // jesli w mapie nie ma pracownika o takim imieniu mamy null i problem
        //return employees.get(name);
    //}

    public static Optional<Employee> find(String name){
        // 3 sposoby utworzenia opcionala
        // return Optional.of(employees.get(name));
        // tworzy nowy optional z zawartością podaną w argumencie
        // ale zawartość może być null
        // ta metoda zakłada że argument nie jest null

        // ta metoda lepsza
        return Optional.ofNullable(employees.get(name));

        // Optional.empty();
        // trzecia metoda, zawsze zwraca pustego optionala
    }


}
