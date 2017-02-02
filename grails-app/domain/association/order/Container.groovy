package association.order

class Container {
	String name

	// List ports
	static hasMany = [ports: ContainerPort]
}
