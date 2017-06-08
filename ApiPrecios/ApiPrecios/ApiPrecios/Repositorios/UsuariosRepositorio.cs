﻿using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Repositorios
{
    public class UsuariosRepositorio : IUsuariosRepositorio
    {
        private DBPrecios db = new DBPrecios();
        // La idea de los repositorios es basicamente que sea uno por entidad
        //por ejemplo usuarios repositorio, listas repositorio etc, 
        //y cada uno tenga los metodos referidos a las llamadas 
        //correspondientes a la base de datos para cada entidad (crear, eliminar, buscar, etc)
        public Usuario ObtenerUsuarioPorIdGoogle(string idGoogle)
        {
            return db.Usuarios.Where(u => u.idGogle == idGoogle).FirstOrDefault();
        }
        public Usuario CrearUsuario(string idGoogle) // esto en realidad deberia recibir como parametro un objeto usuario, lo hice asi para mostrar nomas
        {
            var usuario = new Usuario
            {
                idGogle = idGoogle
            };
            db.Usuarios.Add(usuario);
            db.SaveChanges();

            return usuario;
        }
        public bool ExisteUsuario(string idGoogle)
        {
            return db.Usuarios.Any(u => u.idGogle == idGoogle);
        }

    }
}