Coding Project for Swissre job
22.02.2021 Ruidang Wang

Classes: 
LoadBalancer: maintains a list of provider instances, 
     use invocator to decide which provider to use, then forward the request to the provider. 
     does heartbeatcheck for all providers, decide to exclude or include a provider.
SimpleProvider: implements Provider interface 
ProviderFactory: Create Provider 
RandomInvocator: implments Invocator interface, random get a provider from the provider list 
RoundRobinInvocator: implments Invocator interface, get a provider as sequential loop 
AppMain: a console application for demo. give information in console. 
Unit test: tricky functions are tested. 
22.02.2021 Ruidang Wang