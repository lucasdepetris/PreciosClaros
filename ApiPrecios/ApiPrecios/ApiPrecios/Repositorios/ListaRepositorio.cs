using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ApiPrecios.Repositorios.Abstracciones;
using ApiPrecios.Models.Entidades;

namespace ApiPrecios.Repositorios
{
    public class ListaRepositorio : IListasRepositorio
    {
        private DBPrecios db = new DBPrecios();
        public Lista CrearLista()
        {
            var lista = new Lista();
            lista.fechaCreacion = DateTime.Now;
            db.Listas.Add(lista);
            db.SaveChanges();
            return lista;

        }
    }
}