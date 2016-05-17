package com.lucareto.db

import java.lang.reflect.Type

import spock.lang.Shared
import spock.lang.Specification

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.lucareto.db.model.User

class UserDBTest extends Specification{

    def userDB = new UserDB();
    def Gson gson = new GsonBuilder().create();
    
    def @Shared createdUser;
    
    def "Creates User"() {
        given:
            User user = new User()
            user.setName("Lucareto")
            user.setEmail("lucareto@gmail.com")
            user.setTitle("Red Knight")
            
            def type = new TypeToken<Map<String, String>>(){}.getType()
            
            def expected = [
                "name": "Lucareto",
                "email": "lucareto@gmail.com",
                "title": "Red Knight"
            ]
            
        when:
           createdUser = gson.fromJson(gson.toJson(userDB.addUser(user)),type);
           def result = [:] << createdUser;
           result.remove("id")
           
        then:
        expected == result
    }
    
    def "Deletes User"() {
        given:
        
        when:
          def result = userDB.deleteUser(createdUser.id);
          
        then:
          true == result
          null == userDB.getUser(createdUser.id);
    }
}

