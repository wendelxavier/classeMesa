public class Mesa {
        private int numMesa, capacidadeMax;
        private String  situacaoMesa;

        public Mesa () {
        }

        public Mesa(int numMesa, int capacidadeMax, String situacaoMesa) {
            this.numMesa = numMesa;
            this.capacidadeMax = capacidadeMax;
            this.situacaoMesa = situacaoMesa;
        }

        public int getNumMesa() {
            return numMesa;
        }

        public void setNumMesa(int numMesa) {
            this.numMesa = numMesa;
        }

        public int getCapacidadeMax() {
            return capacidadeMax;
        }

        public void setCapacidadeMax(int capacidadeMax) {
            this.capacidadeMax = capacidadeMax;
        }

        public String getSituacaoMesa() {
            return situacaoMesa;
        }

        public void setSituacaoMesa(String situacaoMesa) {
            this.situacaoMesa = situacaoMesa;
        }


}

