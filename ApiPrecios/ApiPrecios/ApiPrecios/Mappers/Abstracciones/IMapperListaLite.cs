using ApiPrecios.Models.Entidades;
using ApiPrecios.Models.Entidades.Lites;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.Mappers.Abstracciones
{
    public interface IMapperListaLite
    {
       ListaLite MapearListaEntityAListLite(Lista lista);
       ListaCabecera MapearListaEntityAListCabecera(Lista lista);
    }
}
