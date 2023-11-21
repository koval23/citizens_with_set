package ait.citizens.dao;

import ait.citizens.model.Person;

import java.time.LocalDate;
import java.util.*;

public class CitizensImpl implements Citizens {
    /*TODO
     *  переделать под более удобную структуру данных
     *  1 - не должно быть дубликатов
     *  дубликаты по ID
     * */
    private TreeSet<Person> idList;
    private TreeSet<Person> lastNameList;
    private TreeSet<Person> ageList;
    //      ----------------------- comparator -------------------------------------------
//todo lastNameComparator чтоб сортировать по lastName и ID
    private static Comparator<Person> lastNameComparator = (p1, p2) -> {
        int res = p1.getLastName().compareTo(p2.getLastName());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };
    //todo ageComparator чтоб сортировать по возрасту и ID

    private static Comparator<Person> ageComparator = (p1, p2) -> {
        int res = Integer.compare(p1.getAge(), p2.getAge());
        return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
    };

    //      ----------------------- constructor -------------------------------------------
//    todo пустой конструктор
    public CitizensImpl() {
//        TODO
        idList = new TreeSet<>((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        lastNameList = new TreeSet<>(lastNameComparator);
        ageList = new TreeSet<>(ageComparator);
    }

    //    todo конструктор с List
    public CitizensImpl(List<Person> citizens) {
//        TODO
        this();
        for (Person person : citizens) {
            add(person);
        }
    }

    //      ------------------------- methods -----------------------------------------
//O(log n)
//    todo add добавляем Person и сразу держим в отсортировном порядке
    @Override
    public boolean add(Person person) {
        if (person == null) return false;
        idList.add(person);
        lastNameList.add(person);
        ageList.add(person);
        return true;
    }

    //O(n)
//    todo remove удаляем по id сразу из всех листов
    @Override
    public boolean remove(int id) {
        Person victim = find(id);
        if (victim == null) return false;
//        -------------------
        idList.remove(victim);
        ageList.remove(victim);
        lastNameList.remove(victim);
        return true;
    }

    //O(log n)
// todo находим по id одного Person
    @Override
    public Person find(int id) {
        Person pattern = new Person(id, null, null, null);
        Person res = idList.floor(pattern);
        if (res.getId() == id) {
            return res;
        } else {
            return null;
        }
    }

    //O(log n)
// todo находим по lastName одного Person
    @Override
    public Iterable<Person> find(String lastName) {
//        TODO
        Person patternMin = new Person(Integer.MIN_VALUE, null, lastName, null);

        Person patternMax = new Person(Integer.MAX_VALUE, null, lastName, null);
        return lastNameList.subSet(patternMin, patternMax);
    }

    //O(log n)
//    todo находим диапазон по возрасту
    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        LocalDate now = LocalDate.now();
        Person patternMin = new Person(Integer.MIN_VALUE, null, null, now.minusYears(minAge));
        Person patternMax = new Person(Integer.MAX_VALUE, null, null, now.minusYears(maxAge));
        return ageList.subSet(patternMin, true, patternMax, false);
    }

    //O(1)
//    todo возращаем отсортированый по id List
    @Override
    public Iterable<Person> getAllPersonsSortedById() {
//        TODO
        return idList;
    }

    //O(1)
    //    todo возращаем отсортированый по age List
    @Override
    public Iterable<Person> getAllPersonsSortedByAge() {
//        TODO
        return ageList;
    }

    //O(1)
    //    todo возращаем отсортированый по lastName List
    @Override
    public Iterable<Person> getAllPersonsSortedByLastName() {
//        TODO
        return lastNameList;
    }

    //O(1)
    @Override
    public int size() {
        return idList.size();
    }
}
