namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Precio
    {
        [Key]
        [Column("_id")]
        public int C_id { get; set; }

        [StringLength(150)]
        public string id_comercio { get; set; }

        [StringLength(250)]
        public string id_articulo { get; set; }

        [Column("precio")]
        public decimal? precio1 { get; set; }

        public virtual Articulo Articulo { get; set; }

        public virtual Comercio Comercio { get; set; }
    }
}
