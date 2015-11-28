package pnb.orp.guice;

import com.google.inject.AbstractModule;

public class ORPModule extends AbstractModule {

	@Override
	protected void configure() {
		//Bind our implementation of the cache to the interface.
		//bind(ORPCache.class).to(CoreCache.class);
		//install(new FactoryModuleBuilder().implement(ORPCache.class, CoreCache.class).build(CoreCacheFactory.class));
	}

}