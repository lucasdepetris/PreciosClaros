using ApiPrecios.Models.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.Repositorios.Abstracciones
{
    public interface IUsuariosRepositorio
    {
        Usuario ObtenerUsuarioPorIdGoogle(string idGoogle);
        Usuario CrearUsuario(Usuario user);
        bool ExisteUsuario(string idGoogle);
    }
}
