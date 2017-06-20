using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Models.Entidades.Lites
{
    public class UsuarioLite
    {
        public int id { get; set; }
        public string idGogle { get; set; }
        public Nullable<System.DateTime> fechaRegistro { get; set; }
        public string Nombre { get; set; }
        public string apellido { get; set; }
        public string Email { get; set; }
    }
}