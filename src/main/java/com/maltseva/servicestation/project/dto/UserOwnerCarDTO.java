package com.maltseva.servicestation.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOwnerCarDTO extends UserDTO {

    private Set<Long> carSet;
}
