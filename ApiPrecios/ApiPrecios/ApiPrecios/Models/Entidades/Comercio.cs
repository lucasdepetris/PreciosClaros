namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Comercio
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Comercio()
        {
            Precios = new HashSet<Precio>();
        }

        [Column("_id")]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int C_id { get; set; }

        [StringLength(150)]
        public string id { get; set; }

        public int? comercio_id { get; set; }

        [StringLength(250)]
        public string comercio_razon_social { get; set; }

        public int? bandera_id { get; set; }

        [StringLength(150)]
        public string bandera_descripcion { get; set; }

        [StringLength(150)]
        public string sucursal_tipo { get; set; }

        [StringLength(150)]
        public string sucursal_nombre { get; set; }

        [StringLength(150)]
        public string provincia { get; set; }

        [StringLength(150)]
        public string localidad { get; set; }

        [StringLength(150)]
        public string direccion { get; set; }

        [StringLength(150)]
        public string lat { get; set; }

        [StringLength(150)]
        public string lng { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Precio> Precios { get; set; }
    }
}
