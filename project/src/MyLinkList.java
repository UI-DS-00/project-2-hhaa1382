public class MyLinkList {
    private class Node{
        private Node next;
        private Music music;

        Node(Node next,Music music){
            this.next=next;
            this.music=music;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Music getMusic() {
            return music;
        }

        public void setMusic(Music music) {
            this.music = music;
        }
    }
}
