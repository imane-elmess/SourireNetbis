package ma.sourireNetbis.dao.filebase_implementations;

import ma.sourireNetbis.dao.api.IUserDao;
import ma.sourireNetbis.dao.exceptions.DaoException;
import ma.sourireNetbis.model.entities.User;
import ma.sourireNetbis.model.enums.RoleUser;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    private static final File userFile = new File("myFileBase/users.txt");

    // Méthode pour convertir un utilisateur en ligne texte
    private String mapToLine(User user) {
        return user.getId() + "|" +
                user.getUsername() + "|" +
                user.getPassword() + "|" +
                user.getRole();
    }
    // Méthode pour convertir une ligne texte en objet User
    private User mapToUser(String fileLine) throws DaoException {

        try {
            String[] fields = fileLine.split("\\|");
            Integer id = Integer.parseInt(fields[0]);
            String username = fields[1];
            String password = fields[2];
            RoleUser role = RoleUser.valueOf(fields[3]);
            return new User(id, username, password, role);
        } catch (Exception e) {
            throw new DaoException("Erreur lors du mapping de la ligne : " );
        }
    }
    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(userFile.toPath());
            lines.remove(0); //Supprimer la ligne entete
            for(String line: lines){
               users.add(mapToUser(line));
            }
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        return users;
    }

    @Override
    public User findById(Integer identity) throws DaoException {
        return findAll().stream().filter(u -> u.getId()==identity)
                .findFirst()  // Récupère le premier résultat
                .orElse(null);  // Retourne null si aucun patient n'a été trouvé
    }

    @Override
    public User save(User newElement) throws DaoException {
        try(BufferedWriter writer = new BufferedWriter((new FileWriter(userFile,true)))){
            Integer maxId = findAll().stream().mapToInt(User::getId)
                    .max().orElse(0);
            newElement.setId(++maxId);
            writer.write(mapToLine(newElement));
            writer.newLine();
            return newElement;
        } catch (IOException e) {
            throw new DaoException(e.getMessage());}    }

    @Override
    public void update(User newValuesElement) throws DaoException {
        boolean isUpdated = false;
        File tempFile = new File(userFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(userFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        )
        {
            String line;
            while ((line = reader.readLine())!=null){
                String currentID = line.split("\\|")[0];
                if(currentID.equals(newValuesElement.getId().toString()))//je compare etant un String
                {
                    writer.write(mapToLine(newValuesElement));
                    isUpdated=true;
                }
                else{
                    writer.write(line);
                }
                writer.newLine();
            }
            if(!isUpdated) throw new DaoException("Aucune ligne n'a été modifiée!");

        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        if(!userFile.delete()) throw new DaoException("Problème Technique dans la modification");
        if(!tempFile.renameTo(userFile)) throw new DaoException("Problème Technique dans la modification");
    }

    @Override
    public void deleteById(Integer identity) throws DaoException {
        boolean isDeleted = false;
        File tempFile = new File(userFile.getAbsolutePath()+".tmp");
        try(
                BufferedReader reader = new BufferedReader(new FileReader(userFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
        )
        {
            String line;
            while ((line = reader.readLine())!=null){
                String currentID = line.split("\\|")[0];
                if(currentID.equals(identity.toString()))//je compare etant un String
                {
                    isDeleted=true;
                    continue;//on ne l'écrit pas cette ligne
                }
                else{
                    writer.write(line);
                }
                writer.newLine();
            }
            if(!isDeleted) throw new DaoException("Aucune ligne n'a été supprimée!");

        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
        if(!userFile.delete()) throw new DaoException("Problème Technique dans la suppression");
        if(!tempFile.renameTo(userFile)) throw new DaoException("Problème Technique dans la suppression");
    }

    public static void main(String[] args) {
        System.out.println(new UserDao().findById(1));
    }
}
