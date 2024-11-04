package org.example;

public class Concello {
    private int idConcello;
    private PredDiaConcello prediccion;

    public Concello(int idConcello, PredDiaConcello prediccion) {
        this.idConcello = idConcello;
        this.prediccion = prediccion;
    }

    public int getIdConcello() {
        return idConcello;
    }

    public void setIdConcello(int idConcello) {
        this.idConcello = idConcello;
    }

    public PredDiaConcello getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(PredDiaConcello prediccion) {
        this.prediccion = prediccion;
    }

    public class PredDiaConcello {

        private  Ceo CEO;
        private String dataPredicion;
        private String nivelAviso;
        private Pchoiva pchoiva;
        private int tMax;
        private int tMin;
        private Vento vento;

        public PredDiaConcello(Ceo CEO, String dataPredicion, String nivelAviso, Pchoiva pchoiva, int tMax, int tMin, Vento vento) {
            this.CEO = CEO;
            this.dataPredicion = dataPredicion;
            this.nivelAviso = nivelAviso;
            this.pchoiva = pchoiva;
            this.tMax = tMax;
            this.tMin = tMin;
            this.vento = vento;
        }

        public Ceo getCEO() {
            return CEO;
        }

        public void setCEO(Ceo CEO) {
            this.CEO = CEO;
        }

        public String getDataPredicion() {
            return dataPredicion;
        }

        public void setDataPredicion(String dataPredicion) {
            this.dataPredicion = dataPredicion;
        }

        public String getNivelAviso() {
            return nivelAviso;
        }

        public void setNivelAviso(String nivelAviso) {
            this.nivelAviso = nivelAviso;
        }

        public Pchoiva getPchoiva() {
            return pchoiva;
        }

        public void setPchoiva(Pchoiva pchoiva) {
            this.pchoiva = pchoiva;
        }

        public int gettMax() {
            return tMax;
        }

        public void settMax(int tMax) {
            this.tMax = tMax;
        }

        public int gettMin() {
            return tMin;
        }

        public void settMin(int tMin) {
            this.tMin = tMin;
        }

        public Vento getVento() {
            return vento;
        }

        public void setVento(Vento vento) {
            this.vento = vento;
        }

        public class Ceo{

            private int manha;
            private int noite;
            private int tarde;

            public Ceo(int manha, int noite, int tarde) {
                this.manha = manha;
                this.noite = noite;
                this.tarde = tarde;
            }

            public int getManha() {
                return manha;
            }

            public void setManha(int manha) {
                this.manha = manha;
            }

            public int getNoite() {
                return noite;
            }

            public void setNoite(int noite) {
                this.noite = noite;
            }

            public int getTarde() {
                return tarde;
            }

            public void setTarde(int tarde) {
                this.tarde = tarde;
            }
        }

        public class Pchoiva{

            private int manha;
            private int noite;
            private int tarde;

            public Pchoiva(int manha, int noite, int tarde) {
                this.manha = manha;
                this.noite = noite;
                this.tarde = tarde;
            }

            public int getManha() {
                return manha;
            }

            public void setManha(int manha) {
                this.manha = manha;
            }

            public int getNoite() {
                return noite;
            }

            public void setNoite(int noite) {
                this.noite = noite;
            }

            public int getTarde() {
                return tarde;
            }

            public void setTarde(int tarde) {
                this.tarde = tarde;
            }
        }

        public class Vento{
            private int manha;
            private int noite;
            private int tarde;

            public Vento(int manha, int noite, int tarde) {
                this.manha = manha;
                this.noite = noite;
                this.tarde = tarde;
            }

            public int getManha() {
                return manha;
            }

            public void setManha(int manha) {
                this.manha = manha;
            }

            public int getNoite() {
                return noite;
            }

            public void setNoite(int noite) {
                this.noite = noite;
            }

            public int getTarde() {
                return tarde;
            }

            public void setTarde(int tarde) {
                this.tarde = tarde;
            }
        }
    }
}
