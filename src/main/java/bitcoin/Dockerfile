# with help from:
# https://github.com/ruimarinho/docker-bitcoin-core/blob/master/0.21/alpine/Dockerfile


# -----------------------------------------------------------------
# install berkeleydb (for wallet)
# -----------------------------------------------------------------
FROM alpine:3.14 as berkeleydb

RUN apk --no-cache add autoconf
RUN apk --no-cache add automake
RUN apk --no-cache add build-base
RUN apk --no-cache add libressl

ENV BERKELEYDB_VERSION=db-4.8.30.NC
ENV BERKELEYDB_PREFIX=/opt/${BERKELEYDB_VERSION}

#COPY db-4.8.30.NC.tar.gz ./
RUN wget https://download.oracle.com/berkeley-db/${BERKELEYDB_VERSION}.tar.gz
RUN tar -xzf *.tar.gz
RUN sed s/__atomic_compare_exchange/__atomic_compare_exchange_db/g -i ${BERKELEYDB_VERSION}/dbinc/atomic.h
RUN mkdir -p ${BERKELEYDB_PREFIX}

WORKDIR /${BERKELEYDB_VERSION}/build_unix

RUN ../dist/configure --enable-cxx --disable-shared --with-pic --prefix=${BERKELEYDB_PREFIX}
RUN make -j4
RUN make install
RUN rm -rf ${BERKELEYDB_PREFIX}/docs

# -----------------------------------------------------------------
# install bitcoin core
# -----------------------------------------------------------------
FROM alpine as bitcoin-core

COPY --from=berkeleydb /opt /opt

RUN apk --no-cache add git
RUN apk --no-cache add autoconf
RUN apk --no-cache add automake
RUN apk --no-cache add boost-dev
RUN apk --no-cache add build-base
RUN apk --no-cache add chrpath
RUN apk --no-cache add file
RUN apk --no-cache add gnupg
RUN apk --no-cache add libevent-dev
RUN apk --no-cache add libressl
RUN apk --no-cache add libtool
RUN apk --no-cache add linux-headers
RUN apk --no-cache add zeromq-dev

ENV BITCOIN_VERSION=22.0
ENV BITCOIN_PREFIX=/opt/bitcoin

#COPY bitcoin /bitcoin
RUN git clone https://github.com/bitcoin/bitcoin/

WORKDIR /bitcoin

RUN ./autogen.sh
RUN ./configure LDFLAGS=-L`ls -d /opt/db*`/lib/ CPPFLAGS=-I`ls -d /opt/db*`/include/ \
   --prefix=${BITCOIN_PREFIX} \
   --mandir=/usr/share/man \
   --disable-tests \
   --disable-bench \
   --disable-ccache \
   --with-gui=no \
   --with-utils \
   --with-libs \
   --with-daemon
RUN make -j4
RUN make install

WORKDIR /opt/bitcoin/bin

# start server in regtest mode
RUN ./bitcoind -regtest -daemon -fallbackfee=0

# generate wallet
RUN ./bitcoin-cli -regtest createwallet "mywallet"

# generate blocks
RUN ./bitcoin-cli -regtest -generate 101

# get balance
RUN ./bitcoin-cli -regtest getbalance

