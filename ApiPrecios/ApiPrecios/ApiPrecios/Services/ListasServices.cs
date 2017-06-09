using ApiPrecios.Models.Entidades;
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
        public ListasServices(IListasRepositorio repo)
        {
            ListasRepo = repo;
        }
        public Lista CrearLista(String idGoogle)
        {
            return ListasRepo.CrearLista(idGoogle);
        }
        public Boolean AgregarUsuarioLista(String idGoogle, int idLista)
        {
            return ListasRepo.AgregarUsuarioLista(idGoogle, idLista);
        }
    }
}