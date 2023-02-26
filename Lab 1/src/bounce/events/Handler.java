package bounce.events;

public interface Handler<T> {
	public void run(T object);
}
