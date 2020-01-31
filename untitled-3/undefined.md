# 데이터 모델링 문서 번역

Data Modeling Introduction 데이터 모델링 소개 소개 The key challenge in data modeling is balancing the needs of the application, the performance characteristics of the database engine, and the data retrieval patterns.

* 데이터 모델링의 주요 핵심은 어플리케이션의 요구사항과 데이터베이스 엔진 특유의 성능과 데이터 검색 패턴의 균형을 맞추는 것이다.

  When designing data models, always consider the application usage of the data \(i.e. queries, updates, and processing of the data\) as well as the inherent structure of the data itself.

* 데이터 모델을 설계할때는 데이터 자체의 고유 구조뿐만 아니라 어플리케이션 데이터의 사용 \(쿼리, 업데이트 및 데이터 처리\) 를 고려해야 한다.

Flexible Schema \(유연한 스키마\) Unlike SQL databases, where you must determine and declare a table’s schema before inserting data, MongoDB’s collections, by default, does not require its documents to have the same schema. That is:

* 데이터를 인서트 하기전 테이블 스키마를 결졍하고 선언해야하는 sql 데이터베이스와는 다르게 몽고db 컬렉션은 기본적으로 문서에 동일한 스키마를 필요로 하지 않는다.

The documents in a single collection do not need to have the same set of fields and the data type for a field can differ across documents within a collection.

* 단일 컬렉션의 문서는 동일한 필드 셋을 가질 필요가 없으며 필드의 데이터 유형은 컬렉션 내의 문서마다 다를수 있다.

  To change the structure of the documents in a collection, such as add new fields, remove existing fields, or change the field values to a new type, update the documents to the new structure.

* 새로운 필드 추가, 기존 필드 삭제 또는 필드값을 새유형으로 변경하는등의 컬렉션 구조를 변경한다면 새 구조로 다큐먼트를 업트이트 해라.

This flexibility facilitates the mapping of documents to an entity or an object.

* 이러한 유연성으로 인해 엔티티와 오브젝트를 다큐먼트에 매핑하는게 가능하다.

Each document can match the data fields of the represented entity, even if the document has substantial variation from other documents in the collection. 다큐먼트가 컬렉션의 다른 다큐먼트와 크게 다를지라도 각 다큐먼트에 표시된 엔티티와 데이터 필드가 일치할 수 있다.

In practice, however, the documents in a collection share a similar structure, and you can enforce document validation rules for a collection during update and insert operations. See Schema Validation for details. 그러나 실제로 컬렉션의 다큐먼트는 유사한 구조를 공유 하므로 업데이트 와 인서트 작업 중에 컬렉션에 대한 문서 유효성 검사를 실행할수 있다.

Document Structure \(다큐먼트 구조\)

The key decision in designing data models for MongoDB applications revolves around the structure of documents and how the application represents relationships between data.

* 몽고디비 어플리케이션을 위한 데이터 모델 설계의 중요한 키는 다큐먼트 구조 와 응용프로그램이 데이터 간의 관계를 나타내는 방식을 중심으로 이루어짐

MongoDB allows related data to be embedded within a single document. 몽고디비는 단일 다큐먼트에 관련된 데이터를 포함시킬수 있다.

Atomicity of Write Operations 원자성 쓰기작업

Single Document Atomicity 단일 다큐먼트 원자성

In MongoDB, a write operation is atomic on the level of a single document, even if the operation modifies multiple embedded documents within a single document.

* 몽고디비의 쓰기작업은  단일 문서 내에서 여러 임베디드 문서를 수정하더라도  단일 다큐먼트 레벨의 원자성을 보장한다.

A denormalized data model with embedded data combines all related data in a single document instead of normalizing across multiple documents and collections. This data model facilitates atomic operations. 임베디드된 데이터가 있는 비정규화된 데이터 모델은 여러 다큐먼트 및 컬렉션에서 정규화 하는 대신 모든 관련 데이터를 단일 다큐먼트로 결합한다. 이러한 데이터 모델은 원자성을 가능하게 한다.

When a single write operation \(e.g. db.collection.updateMany\(\)\) modifies multiple documents, the modification of each document is atomic, but the operation as a whole is not atomic. 단일 쓰기 작업\(ex : db.collection.updateMany\(\)\)이 여러 다큐먼트를 수정하는 경우 각 문서는 원자성을 보장하지만 전체적인 작업은 원자성을 보장하지 않는다.

When performing multi-document write operations, whether through a single write operation or multiple write operations, other operations may interleave.

* 단일 쓰기 또는 다중 쓰기 작업을 통해서 다중 다큐먼트 쓰기 작업을 수행할 때 다른 작업이 인터리브 될 수 있다.

  인터리브 : [http://www.terms.co.kr/interleave.htm](http://www.terms.co.kr/interleave.htm)

For situations that require atomicity of reads and writes to multiple documents \(in a single or multiple collections\), MongoDB supports multi-document transactions:

* 다중 다큐먼트에 대해 읽거나 쓰기 원자성이 필요한 상황에서 몽고디비는 다중 다큐먼트 트랜잭션을 지원함.

In version 4.0, MongoDB supports multi-document transactions on replica sets.

* 버전 4.0에서  몽고디비는 리플리카 셋에서  다중 다큐먼트 트랜잭션을 지원한다.

In version 4.2, MongoDB introduces distributed transactions, which adds support for multi-document transactions on sharded clusters and incorporates the existing support for multi-document transactions on replica sets.

* 버전 4.2에서 몽고디비는 분산 트랜잭션을 도입하여 분산 클러스터에 다중 다큐먼트 문서 트랜잭션 지원을 추가하고 리플리카 셋에 다중 다큐먼트 트랜잭션 지원을 통합

For details regarding transactions in MongoDB, see the Transactions page.

In most cases, multi-document transaction incurs a greater performance cost over single document writes, and the availability of multi-document transactions should not be a replacement for effective schema design.

* 대부분의 경우, 다중 다큐먼트 트랜잭션은 단일 다큐먼트 쓰기보다 더 많은 비용이 발생하며 다중 다큐먼트 트랜잭션의 가용성이 효과적인 스키마 디자인을 대신해서는 안된다.
* 즉 다중 다큐먼트 트랜잭션에 의존하지 말고 효과적으로 스키마 디자인을 해서 다중 다큐먼트 트랜잭션을 줄여라?

  For many scenarios, the denormalized data model \(embedded documents and arrays\) will continue to be optimal for your data and use cases. 

  * 많은 시나리오에서 비정규화된 데이터 모델\(임베디드 다큐먼트와 배열\)은 데이터와  사용 케이스에 대해 최적화를 제공할 것이다.

That is, for many scenarios, modeling your data appropriately will minimize the need for multi-document transactions.

For additional transactions usage considerations \(such as runtime limit and oplog size limit\), see also Production Considerations.

