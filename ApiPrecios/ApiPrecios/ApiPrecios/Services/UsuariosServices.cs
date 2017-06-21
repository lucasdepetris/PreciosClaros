using ApiPrecios.Services.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using ApiPrecios.Models.Entidades.Lites;

namespace ApiPrecios.Services
{
    public class UsuariosServices : IUsuariosServices
    {
        private readonly IUsuariosRepositorio usuariosRepo;
        public UsuariosServices(IUsuariosRepositorio repo)
        {
            usuariosRepo = repo;
        }
        public Usuario ObtenerUsuarioPorIdGoogle(string idGoogle)
        {
            //en este caso no tiene tanto sentido el service, pero la idea es que este
            // por si se quiere agregar mas logica, y no es un llamado solo a la base de datos
            return usuariosRepo.ObtenerUsuarioPorIdGoogle(idGoogle);
        }
        public UsuarioLite LogIn(Usuario user)
        {
            if (!usuariosRepo.ExisteUsuario(user.idGogle))
            {
                user = usuariosRepo.CrearUsuario(user);
            }
            user = usuariosRepo.ObtenerUsuarioPorIdGoogle(user.idGogle);
            return new UsuarioLite {
                                     id = user.id,
                                     Nombre = user.Nombre,
                                     apellido = user.apellido,
                                     idGogle = user.idGogle,
                                     Email = user.Email,
                                     fechaRegistro = user.fechaRegistro
                                     };
        }
    }
}