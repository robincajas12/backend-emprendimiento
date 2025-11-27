package com.project007;


import com.project007.db.IUserService;
import com.project007.db.User;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try(SeContainer container = SeContainerInitializer.newInstance().initialize())
        {
          IUserService us =  container.select(IUserService.class).get();
          us.create(User.builder().name("robin").build());
          us.selectAll().forEach(System.out::println);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}