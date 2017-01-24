package confcost.view2;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * 
 * @author Marc Eichler
 *
 */
public class ModelList<E> extends AbstractListModel<E>  {
		private static final long serialVersionUID = -4543163268091806757L;
		
		List<E> iterations = new LinkedList<E>();
		
		@Override
		public int getSize() {
			return iterations.size();
		}
		@Override
		public E getElementAt(int index) {
			return iterations.get(index);
		}
		public void set(List<E> iterations) {
			this.iterations = iterations;
			this.fireContentsChanged(this, 0, this.iterations.size());
		}
}
