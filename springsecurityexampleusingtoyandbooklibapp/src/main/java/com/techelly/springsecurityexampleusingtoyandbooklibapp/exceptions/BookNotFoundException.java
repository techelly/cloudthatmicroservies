package com.techelly.springsecurityexampleusingtoyandbooklibapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookNotFoundException extends Exception {

	private String msg;
	
}