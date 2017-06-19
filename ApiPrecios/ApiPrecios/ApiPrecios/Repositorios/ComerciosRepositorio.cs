using ApiPrecios.Models.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Repositorios
{
    public class ComerciosRepositorio : IComerciosRepositorio
    {
        private DBPrecios db = new DBPrecios();
        public bool ExisteComercio(string idComercio)
        {
            return db.Comercios.Any(c => c.id == idComercio);           
        }
        public void AgregarComercio(Comercio comercio)
        {
            db.Comercios.Add(comercio);
            db.SaveChanges();
        }
    }
}