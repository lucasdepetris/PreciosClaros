namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    public partial class Usuario
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public Usuario()
        {
            Listas = new HashSet<Lista>();
        }

        public int id { get; set; }

        [StringLength(60)]
        public string idGogle { get; set; }
        [StringLength(60)]
        public string Email { get; set; }

        [StringLength(30)]
        public string Nombre { get; set; }

        [StringLength(30)]
        public string Apellido { get; set; }

        public DateTime? fechaRegistro { get; set; }

        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Lista> Listas { get; set; }
    }
}
