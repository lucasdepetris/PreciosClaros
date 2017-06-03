namespace ApiPrecios.Models.Entidades
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class DBPrecios : DbContext
    {
        public DBPrecios()
            : base("name=DBPrecios")
        {
        }

        public virtual DbSet<Articulo> Articulos { get; set; }
        public virtual DbSet<Categoria> Categorias { get; set; }
        public virtual DbSet<Comercio> Comercios { get; set; }
        public virtual DbSet<ListaArticulo> ListaArticuloes { get; set; }
        public virtual DbSet<Lista> Listas { get; set; }
        public virtual DbSet<Precio> Precios { get; set; }
        public virtual DbSet<Usuario> Usuarios { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Articulo>()
                .HasMany(e => e.ListaArticuloes)
                .WithRequired(e => e.Articulo)
                .HasForeignKey(e => e.idArticulo)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Articulo>()
                .HasMany(e => e.Precios)
                .WithOptional(e => e.Articulo)
                .HasForeignKey(e => e.id_articulo);

            modelBuilder.Entity<Comercio>()
                .HasMany(e => e.Precios)
                .WithOptional(e => e.Comercio)
                .HasForeignKey(e => e.id_comercio);

            modelBuilder.Entity<ListaArticulo>()
                .Property(e => e.precioOptimo)
                .HasPrecision(18, 4);

            modelBuilder.Entity<ListaArticulo>()
                .Property(e => e.precioReal)
                .HasPrecision(18, 4);

            modelBuilder.Entity<Lista>()
                .HasMany(e => e.ListaArticuloes)
                .WithRequired(e => e.Lista)
                .HasForeignKey(e => e.idLista)
                .WillCascadeOnDelete(false);

            modelBuilder.Entity<Lista>()
                .HasMany(e => e.Usuarios)
                .WithMany(e => e.Listas)
                .Map(m => m.ToTable("UsuariosListas").MapLeftKey("idLista").MapRightKey("idUsuario"));

            modelBuilder.Entity<Precio>()
                .Property(e => e.precio1)
                .HasPrecision(18, 4);
        }
    }
}
