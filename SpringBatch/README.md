### Spring Batch

Spring Batch is one of the spring core modules. Allows to create robust 
batch processing systems.

Batch processing is a technique which processes data in a large group 
instead of a single element of data.
High volume of data are processed with minimal human interaction.

#### Architecture

Job Repository for state management.

Job Launcher ---> Job ---> Steps 

Step has three components

- ItemReader

- ItemProcessor

- ItemWriter

