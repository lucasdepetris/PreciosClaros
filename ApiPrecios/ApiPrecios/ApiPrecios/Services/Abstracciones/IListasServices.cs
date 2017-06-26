using ApiPrecios.Models.Entidades;
using ApiPrecios.Models.Entidades.Lites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.Services.Abstracciones
{
    public interface IListasServices
    {
        ListaLite CrearLista(int idUsuario, string nombre, string descripcion);
        Boolean AgregarUsuarioLista(String idGoogle, int idLista);
        IEnumerable<ListaCabecera> ObtenerListas(int idUsuario);
        ListaLite ObtenerLista(int idLista);
        ListaLite AgregarProducto(int idLista, string idArticulo, int cantidad, int precioOptimo, string idComercio);
        IEnumerable<ListaCabecera> ModificarLista(int idLista,String nombre, String descripcion ,int idUsuario);
        ListaLite ModificarCantidadDeUnProducto(int idLista, String idArticulo, int cantidad);
        IEnumerable<ListaCabecera> EliminarLista(int idLista ,int idUsuario);
        ListaLite EliminarProducto(String idArticulo,int idLista);
    }
}
