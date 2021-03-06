﻿using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Repositorios
{
    public class ProductosRepositorio : IProductosRepositorio
    {
        private DBPrecios db = new DBPrecios();
        public IList<Articulo> buscarProductos(string buscar)
        {
            buscar = buscar.ToLower();
            var buscarSplit = buscar.Split(' ');
            
            return buscarPorNombre(buscarSplit, buscar);
        }
        public bool ExisteProducto(string codigoProducto)
        {
            return db.Articulos.Any(a => a.id == codigoProducto);

        }
        public void AgregarProducto(Articulo articulo)
        {
            db.Articulos.Add(articulo);
            db.SaveChanges();
        }
        private List<Articulo> buscarPorNombre(string[] buscarSplit, string buscar)
        {
            var primarios = (from p in db.Articulos
                             where p.nombre.ToLower().Contains(buscar)
                             || p.marca.ToLower().Contains(buscar)
                             select p).ToList();
            var secundarios = (from p in db.Articulos
                               where buscarSplit.Contains(p.nombre.ToLower())
                               || buscarSplit.Contains(p.marca.ToLower())
                               select p).ToList();
            primarios.AddRange(secundarios);

            return primarios.Distinct().ToList();
        }
    }
}