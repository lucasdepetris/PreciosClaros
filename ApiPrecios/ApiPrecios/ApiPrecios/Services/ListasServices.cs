using ApiPrecios.Mappers.Abstracciones;
using ApiPrecios.Models.Entidades;
using ApiPrecios.Models.Entidades.Lites;
using ApiPrecios.PreciosClaros.Entidades;
using ApiPrecios.Repositorios.Abstracciones;
using ApiPrecios.Services.Abstracciones;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ApiPrecios.Services
{
    public class ListasServices : IListasServices
    {
        private readonly IListasRepositorio ListasRepo;
        private readonly IMapperListaLite mapper;
        public ListasServices(IListasRepositorio repo, IMapperListaLite mapperList)
        {
            ListasRepo = repo;
            mapper = mapperList;
        }
        public ListaLite CrearLista(int idUsuario, string nombre, string descripcion)
        {
            var lista = ListasRepo.CrearLista(idUsuario, nombre, descripcion);
            return mapper.MapearListaEntityAListLite(lista);
        }
        public IEnumerable<ListaCabecera> ObtenerListas(int idUsuario)
        {
            var listas = ListasRepo.ObtenerListas(idUsuario).ToList();
            return listas.Select(l => mapper.MapearListaEntityAListCabecera(l));
        }
        public ListaLite AgregarProducto(int idLista, string idArticulo, int cantidad, int precioOptimo, string idComercio)
        {
            //Aca habria que validar si el idArticulo y el idComercio existen en nuestra bdd o explotaria todo
            ListasRepo.AgregarProducto(idLista, idArticulo, cantidad, precioOptimo, idComercio);
            return ObtenerLista(idLista);
        }
        public ListaLite ObtenerLista(int idLista)
        {
            var lista = ListasRepo.ObtenerLista(idLista);
            return mapper.MapearListaEntityAListLite(lista);
            
        }
        public Boolean AgregarUsuarioLista(String idGoogle, int idLista)
        {
            return ListasRepo.AgregarUsuarioLista(idGoogle, idLista);
        }
        public IEnumerable<ListaCabecera> ModificarLista(int idLista, String nombre, String descripcion,int idUsuario)
        {
            ListasRepo.ModificarLista(idLista, nombre, descripcion);
            return ObtenerListas(idUsuario);
        }
    }
}