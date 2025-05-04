package dao;

import model.*;
import model.Class;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface InterfaceDAO <T>{
    public void add(T t);

    public void update(T t);

    public void delete(T t);

    public T selectById(int id);

    public T selectByName(String name);

    public ArrayList<T> selectAll();

    ArrayList<T> selectByCondition(String condition);

}
