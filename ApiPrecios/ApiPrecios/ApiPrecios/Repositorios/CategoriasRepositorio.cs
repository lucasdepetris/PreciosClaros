using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Repositorios
{
    public class CategoriasRepositorio : ICategoriasRepositorio
    {
        private DBPrecios db = new DBPrecios();

        public IList<Categoria> buscarCategorias(string buscar)
        {
            buscar = buscar.ToLower();
            string[] buscarSplit = buscar.Split(' ');
            var categorias = (from c in db.Categorias
                              where c.nombre.ToLower().Contains(buscar)
                              select c).ToList().First();
            if(categorias != null)
            return new List<Categoria> { categorias }.Distinct().ToList();

            return new List<Categoria>();

        }
    }
}