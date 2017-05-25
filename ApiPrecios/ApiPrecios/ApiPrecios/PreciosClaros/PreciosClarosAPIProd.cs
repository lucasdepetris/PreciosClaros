using ApiPrecios.PreciosClaros.Entidades;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using Newtonsoft.Json;

namespace ApiPrecios.PreciosClaros
{
    public class PreciosClarosAPIProd : IPreciosClarosApi
    {
        internal string APIURL = "https://d735s5r2zljbo.cloudfront.net/prod/";
        public List<Sucursal> ObtenerSucursalesPorZona(double lat, double lng, int limite)
        {
          var json = getHttpRequest("sucursales?lat=" + lat + "&lng=" + lng + "&limit=" + limite);         
          return JsonConvert.DeserializeObject<List<Sucursal>>(json.SelectToken("sucursales").ToString());
        }
        private JToken getHttpRequest(string url)
        {
            var json = new WebClient().DownloadString(APIURL + url);
            return JObject.Parse(json);
        }
    }
}