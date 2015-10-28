package png.orp.guice;

import com.google.inject.AbstractModule;

import pnb.orp.cache.CoreCache;
import pnb.orp.cache.ORPCache;

public class ORPModule extends AbstractModule {

	@Override
	protected void configure() {
		//Bind our implementation of the cache to the interface.
		bind(ORPCache.class).to(CoreCache.class);
		//install(new FactoryModuleBuilder().implement(ORPCache.class, CoreCache.class).build(CoreCacheFactory.class));
	}

}