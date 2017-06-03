namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Categoria
    {
        [Column("_id")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int C_id { get; set; }

        [StringLength(150)]
        public string id { get; set; }

        [StringLength(150)]
        public string nombre { get; set; }

        public int? nivel { get; set; }

        public int? cant_productos { get; set; }
    }
}
