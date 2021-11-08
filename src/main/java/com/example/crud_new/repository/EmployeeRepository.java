package com.example.crud_new.repository;

import com.example.crud_new.model.Employee;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;//crud hash

    private ListOperations listOperations;//curd list

    private SetOperations setOperations;//crud set

    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {
//        this.hashOperations = redisTemplate.opsForHash();//hash

//        this.listOperations = redisTemplate.opsForList();//list

        this.setOperations = redisTemplate.opsForSet();//set

        this.redisTemplate = redisTemplate;

    }

    public void saveEmployee(Employee employee){
//        hashOperations.put("EMPLOYEE", employee.getId(), employee);//hash

//        listOperations.leftPush("EMPLOYEE",employee);//list

        setOperations.add("EMPLOYEE",employee);//set

    }
    public Set<Employee> findAll(){
//        return hashOperations.values("EMPLOYEE");//hash

//        return listOperations.range("EMPLOYEE",0,listOperations.size("EMPLOYEE"));//list

        return setOperations.members("EMPLOYEE");
    }
    public Employee findById(Integer id){
//        return (Employee) hashOperations.get("EMPLOYEE", id);//hash

        //list
//        List<Employee> list = listOperations.range("EMPLOYEE",0,listOperations.size("EMPLOYEE"));
////        for (Employee employee : list){
////            if(employee.getId()==id){
////                return employee;
////            }
////        }
////        return null;

        //set
        Set<Employee> set = this.findAll();
        for (Employee employee: set){
            if(employee.getId()==id){
                return employee;
            }
        }
        return null;
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }

    public void delete(Integer id){
//        hashOperations.delete("EMPLOYEE", id);//hash

//        listOperations.rightPopAndLeftPush("EMPLOYEE",id);//list

        Employee employee = this.findById(id);
        setOperations.remove("EMPLOYEE",id);
    }
}
