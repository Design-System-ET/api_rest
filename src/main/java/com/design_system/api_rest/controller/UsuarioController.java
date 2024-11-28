package com.design_system.api_rest.controller;

import com.design_system.api_rest.dao.UsuariosDao;
import com.design_system.api_rest.interfaces.Resources;
import static com.design_system.api_rest.interfaces.Resources.PRINCIPAL;
import com.design_system.api_rest.pojo.Usuario;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping(PRINCIPAL)
@CrossOrigin(origins = "*")
public class UsuarioController implements Resources{
    
    @GetMapping()
    public String inicio(){
        return "Aqui inicio la API de mensajeria";
    }
    
    
    //aqui envio los datos en la misma url #ejemplo 1
    @GetMapping(MENSAJE)
    public String holaNombre(@PathVariable String nombre, @PathVariable String apellido){
        return "Tu nombre completo es: " + nombre + " " + apellido;
    }
    
    
    //aqui envio los datos como parametros #ejemplo 2
    @GetMapping(EDAD)
    public String edad(@RequestParam("id") int userId, @RequestParam("nombre") String userNombre){
        return "Tu edad es: " + userId + " y su nombre es: " + userNombre;
    }
    
    
    //aui envio los datos en el body de la request #ejemplo 3
    @PostMapping(USUARIOS)
    public String Usuario(@RequestBody Usuario usuario) {
        return "Usuario creado: " + usuario.getNombre() + " " + usuario.getApellido();
    }
    
    
    
    //para manipular la bd
    @Autowired 
	private UsuariosDao userDAO;
    
    
    //agrega un cliente #ejemplo 4
    @PostMapping(NEW)
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario clientesNAME){
           
        //creo un usuario con los datos recibidos
        Usuario newUsuario = userDAO.save(clientesNAME);
        return ResponseEntity.ok(newUsuario);
    }
    
    
    //obtengo una lista de usuarios completa de la bd #ejemplo 5
    @GetMapping(LISTA)
    public @ResponseBody Map<String, Object> getUsuarios(){
        
        //creo una list de usuarios
        List<Usuario> usuarios = userDAO.findAll();
        
        //devulvo la respuesta en formarto json
        Map<String, Object> response = new HashMap<>();
        response.put("Mensaje", "Usuarios obtenidos correctamente");
        response.put("Usuarios", usuarios);
        return response;
        
    }
    
    
    //eliminar un usuario por id #ejemplo 6
    @DeleteMapping(DELETE)
    public ResponseEntity<Map<String, String>> borrarUsuario(@RequestParam("id") Long id) {
	// Devuelvo la respuesta en formato JSON
        Map<String, String> response = new HashMap<>();

        // Validar si el usuario existe
        if (!userDAO.existsById(id)) {
            response.put("message", "El usuario no existe");

            // Devolver estado 404 NOT_FOUND
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Eliminar usuario
        userDAO.deleteById(id);
        response.put("message", "Se eliminó correctamente el Usuario");

        // Devolver estado 200 OK
        return ResponseEntity.ok(response);
    }
    
    
    
    //eliminar un usuario por id modificando el cuerpo de la respuesta #ejemplo 7
    @DeleteMapping(DEL)
    public ResponseEntity<Map<String, String>> borrarUser(@RequestParam("id") Long id) {

        // Devuelvo la respuesta en formato JSON
        Map<String, String> response = new HashMap<>();

        try {
            if (!userDAO.existsById(id)) {
                response.put("message", "Usuario no encontrado");

                // Agregar encabezados personalizados para este caso
                HttpHeaders headers = new HttpHeaders();
                headers.add("X-Custom-Header", "UsuarioInexistente");

                // Devuelve un estado personalizado
                return new ResponseEntity<>(response, headers, HttpStatus.NOT_FOUND);
            }

            userDAO.deleteById(id);
            response.put("message", "Usuario eliminado correctamente");

            // Agregar encabezado personalizado para éxito
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "OperacionExitosa");

            // Devuelve un estado 200 (OK)
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al eliminar el usuario");

            // Encabezado personalizado en caso de error
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "OperacionFallida");

            // Devuelve un estado 500 (Error interno)
            return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
