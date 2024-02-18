package com.utn.utils;

import com.utn.dto.request.RolDto;
import com.utn.entity.ERol;
import com.utn.entity.Rol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RolObjectUtils {

    public static Rol rol (){
        Rol rol = new Rol();
        rol.setName(ERol.USER);
        return rol;
    }

    public static Rol rol2 (){
        Rol rol = new Rol();
        rol.setName(ERol.ADMIN);
        return rol;
    }

    public static RolDto rolDto (){
        RolDto rol = new RolDto();
        rol.setName(ERol.USER);
        return rol;
    }

    public static RolDto rolDto2 (){
        RolDto rol = new RolDto();
        rol.setName(ERol.ADMIN);
        return rol;
    }

    public static Set<Rol> setRoles() {
        return new HashSet<>(Set.of(rol()));
    }

    public static List<Rol> listaRoles() {
        return new ArrayList<>();
    }

    public static List<Rol> listaRoles2() {
        return new ArrayList<>(setRoles());
    }

    public static List<Rol> listaRoles3() {
        List<Rol> listaRoles = new ArrayList<>();
        listaRoles.add(rol());
        listaRoles.add(rol2());
        return listaRoles;
    }

    public static List<RolDto> listaRolesDto() {
        List<RolDto> listaRoles = new ArrayList<>();
        listaRoles.add(rolDto());
        listaRoles.add(rolDto2());
        return listaRoles;
    }
}
