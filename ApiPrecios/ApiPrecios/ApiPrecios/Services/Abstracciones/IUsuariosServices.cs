﻿using ApiPrecios.Models.Entidades;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ApiPrecios.Services.Abstracciones
{
    public interface IUsuariosServices
    {
        Usuario ObtenerUsuarioPorIdGoogle(string idGoogle);
    }
}
