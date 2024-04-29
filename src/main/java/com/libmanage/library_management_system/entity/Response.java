package com.libmanage.library_management_system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Response {
    private int status;
    private String error;
    private Object post;
    private int roleId;
}
////a generic class that is used to encapsulate responses from api calls.
//public class Response<T> {                   //where T is a placeholder for the type of data that can be stored in the post field allowing flexibility in defining the type of data the Response can hold
//    private int status;
//    private String error;
//    private T[] post;              //this represents the data associated with the response .this could be array of objects allowing the response object to carry additional data based on the api response
//
//}
