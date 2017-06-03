namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("ListaArticulo")]
    public partial class ListaArticulo
    {
        [Key]
        [Column(Order = 0)]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int idLista { get; set; }

        [Key]
        [Column(Order = 1)]
        [StringLength(250)]
        public string idArticulo { get; set; }

        public decimal? precioOptimo { get; set; }

        public decimal? precioReal { get; set; }

        public virtual Articulo Articulo { get; set; }

        public virtual Lista Lista { get; set; }
    }
}
