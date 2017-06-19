[assembly: WebActivatorEx.PreApplicationStartMethod(typeof(ApiPrecios.App_Start.NinjectWebCommon), "Start")]
[assembly: WebActivatorEx.ApplicationShutdownMethodAttribute(typeof(ApiPrecios.App_Start.NinjectWebCommon), "Stop")]

namespace ApiPrecios.App_Start
{
    using System;
    using System.Web;

    using Microsoft.Web.Infrastructure.DynamicModuleHelper;

    using Ninject;
    using Ninject.Web.Common;
    using PreciosClaros;
    using Repositorios.Abstracciones;
    using Repositorios;
    using Services.Abstracciones;
    using Services;
    using Mappers.Abstracciones;
    using Mappers;

    public static class NinjectWebCommon 
    {
        private static readonly Bootstrapper bootstrapper = new Bootstrapper();

        /// <summary>
        /// Starts the application
        /// </summary>
        public static void Start() 
        {
            DynamicModuleUtility.RegisterModule(typeof(OnePerRequestHttpModule));
            DynamicModuleUtility.RegisterModule(typeof(NinjectHttpModule));
            bootstrapper.Initialize(CreateKernel);
        }
        
        /// <summary>
        /// Stops the application.
        /// </summary>
        public static void Stop()
        {
            bootstrapper.ShutDown();
        }
        
        /// <summary>
        /// Creates the kernel that will manage your application.
        /// </summary>
        /// <returns>The created kernel.</returns>
        private static IKernel CreateKernel()
        {
            var kernel = new StandardKernel();
            try
            {
                kernel.Bind<Func<IKernel>>().ToMethod(ctx => () => new Bootstrapper().Kernel);
                kernel.Bind<IHttpModule>().To<HttpApplicationInitializationHttpModule>();

                RegisterServices(kernel);
                return kernel;
            }
            catch
            {
                kernel.Dispose();
                throw;
            }
        }

        /// <summary>
        /// Load your modules or register your services here!
        /// </summary>
        /// <param name="kernel">The kernel.</param>
        private static void RegisterServices(IKernel kernel)
        {
            kernel.Bind<IPreciosClarosApi>().To<PreciosClarosAPIProd>();
            kernel.Bind<IUsuariosRepositorio>().To<UsuariosRepositorio>();
            kernel.Bind<IListasRepositorio>().To<ListaRepositorio>();
            kernel.Bind<IListasServices>().To<ListasServices>();
            kernel.Bind<IUsuariosServices>().To<UsuariosServices>();
            kernel.Bind<IProductosServices>().To<ProductosServices>();
            kernel.Bind<IProductosRepositorio>().To<ProductosRepositorio>();
            kernel.Bind<IMapperListaLite>().To<MapperListaLite>();
        }        
    }
}
