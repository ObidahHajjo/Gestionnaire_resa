package fr.bts.sio.Interface;

import fr.bts.sio.Models.RoleEmployee;

import java.util.List;

public interface RoleEmployeeDAOInterface {
    boolean ajouterRole(RoleEmployee role);
    RoleEmployee chercherRoleParId(int idRole);
    List<RoleEmployee> chercherTousLesRoles();
    boolean modifierRole(RoleEmployee role);
    boolean supprimerRole(int idRole);
}
