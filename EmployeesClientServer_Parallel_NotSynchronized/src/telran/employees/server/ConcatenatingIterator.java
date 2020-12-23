package telran.employees.server;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ConcatenatingIterator<E> implements Iterator<E>, Iterable<E>, Serializable {
	private static final long serialVersionUID = 1L;
	private Iterator<? extends Iterable<E>> externalIterator;
	private Iterator<E> interiorIterator;

	public ConcatenatingIterator(Iterable<? extends Iterable<E>> nestedIterable) {
		externalIterator = nestedIterable.iterator();
		interiorIterator = Collections.emptyIterator();
		enableExternalIterator();
	}

	private void enableExternalIterator() {
		while (externalIterator.hasNext() && !interiorIterator.hasNext()) {
			interiorIterator = externalIterator.next().iterator();
		}
	}

	@Override
	public boolean hasNext() {
		return interiorIterator.hasNext();
	}

	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		E res = interiorIterator.next();
		enableExternalIterator();
		return res;
	}

	@Override
	public Iterator<E> iterator() {
		return this;
	}

}
