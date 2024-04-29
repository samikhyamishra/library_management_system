package com.libmanage.library_management_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BooksDto {
   private int count;
   private int bookId;
   private String userName;
}
