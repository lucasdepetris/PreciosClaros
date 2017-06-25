using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Repositorios
{
    public class PreciosRepositorio : IPreciosRepositorio
    {
        private DBPrecios db = new DBPrecios();
        public void ActualizarPrecio(string idComercio, string idArticulo, decimal precio)
        {
          if(ExistePrecio(idArticulo,idComercio))
            {
               var precioDb = db.Precios.Where(p => p.id_comercio == idComercio && p.id_articulo == idArticulo).FirstOrDefault();
                precioDb.precio1 = precio;
                db.SaveChanges();
            } else
            {
                var precioDb = new Precio { id_articulo = idArticulo,
                                            id_comercio = idComercio,
                                            precio1 = precio
                                           };
                db.Precios.Add(precioDb);
                db.SaveChanges();
            }
        }
        private bool ExistePrecio(string idArticulo, string idComercio)
        {
            return db.Precios.Any(p => p.id_comercio == idComercio && p.id_articulo == idArticulo);
        }
    }
}