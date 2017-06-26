using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ApiPrecios.Models.Entidades;


namespace ApiPrecios.Repositorios.Abstracciones
{
    public interface IListasRepositorio
    {
        Lista CrearLista(int idUsuario, string nombre, string descripcion);
        Boolean AgregarUsuarioLista(String idGoogle , int idLista);
        IEnumerable<Lista> ObtenerListas(int idUsuario);
        Lista ObtenerLista(int id);
        bool AgregarProducto(int idLista, string idArticulo, int cantidad, int precioOptimo, string idComercio);
        bool ModificarLista(int idLista, String nombre, String descripcion);
        bool ModificarCantidadDeUnProducto(int idLista,String idArticulo, int cantidad);
        bool EliminarLista(int idLista);
        bool EliminarProducto(String idArticulo, int idLista);
    }
}