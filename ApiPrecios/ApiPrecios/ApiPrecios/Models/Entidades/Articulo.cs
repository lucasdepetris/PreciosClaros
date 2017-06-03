namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Articulos")]
    public partial class Articulo
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Articulo()
        {
            ListaArticuloes = new HashSet<ListaArticulo>();
            Precios = new HashSet<Precio>();
        }

        [Column("_id")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int C_id { get; set; }

        public int? id_categoria { get; set; }

        [StringLength(250)]
        public string id { get; set; }

        [StringLength(150)]
        public string marca { get; set; }

        [StringLength(250)]
        public string nombre { get; set; }

        [StringLength(150)]
        public string presentacion { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<ListaArticulo> ListaArticuloes { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Precio> Precios { get; set; }
    }
}
